package com.example.Neurosurgical.App.services;

import com.example.Neurosurgical.App.advice.exceptions.EntityNotFoundException;
import com.example.Neurosurgical.App.mappers.QuestionQuizzMapper;
import com.example.Neurosurgical.App.models.dtos.QuestionQuizzDto;
import com.example.Neurosurgical.App.models.entities.QuestionQuizzEntity;
import com.example.Neurosurgical.App.repositories.AnswerQuizzRepository;
import com.example.Neurosurgical.App.repositories.CorrectAnswerQuizzRepository;
import com.example.Neurosurgical.App.repositories.GeneralInfoRepository;
import com.example.Neurosurgical.App.repositories.QuestionQuizzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizzServiceImpl implements QuizzService {

    final private QuestionQuizzRepository questionQuizzRepository;
    final private AnswerQuizzRepository answerQuizzRepository;
    final private CorrectAnswerQuizzRepository correctAnswerQuizzRepository;

    final private GeneralInfoRepository generalInfoRepository;

    @Autowired
    public QuizzServiceImpl(QuestionQuizzRepository questionQuizzRepository,
                            AnswerQuizzRepository answerQuizzRepository,
                            CorrectAnswerQuizzRepository correctAnswerQuizzRepository,
                            GeneralInfoRepository generalInfoRepository) {

        this.questionQuizzRepository = questionQuizzRepository;
        this.answerQuizzRepository = answerQuizzRepository;
        this.correctAnswerQuizzRepository = correctAnswerQuizzRepository;
        this.generalInfoRepository = generalInfoRepository;
    }

    @Override
    public Optional<List<QuestionQuizzDto>> findByCourseId(Long id) throws EntityNotFoundException {

        List<QuestionQuizzDto> listQuestionsQuizz = new ArrayList<>();

        Optional <List<QuestionQuizzEntity>> questionsById = this.questionQuizzRepository.findByIdCourse(id);

        double nrMinutesQuizz = this.generalInfoRepository.findById(1L).get().getQuizTime(); //generalInfoRepository.findById(1);

        if ( questionsById.isEmpty() ){
            throw new EntityNotFoundException("Question", id);
        }

        int totalQuestions = questionsById.get().size();

        List<Integer> lectures = new ArrayList<>();
        List<Integer> nrQuestionsPerLecture = new ArrayList<>();

        //System.out.println("\n\nTotal questions: \n" + totalQuestions + "\n");

        double meanDifficulty = questionsById.get().stream().mapToInt(QuestionQuizzEntity::getDifficulty).average().orElse(5); // calculate the mean difficulty
        double sdDifficulty = 5;// calculate the standard deviation of difficulty

        double averageTimePerQuestion = 0;

        for( QuestionQuizzEntity question : questionsById.get()){
            if( !lectures.contains(question.getLectureNumber())){
                lectures.add(question.getLectureNumber());
                nrQuestionsPerLecture.add(0);
            }

            int indexOfLecture = lectures.indexOf(question.getLectureNumber());
            nrQuestionsPerLecture.set(indexOfLecture, nrQuestionsPerLecture.get(indexOfLecture) + 1);

            sdDifficulty += Math.pow(question.getDifficulty() - meanDifficulty, 2);

            averageTimePerQuestion += question.getTimeMinutes();
        }

        sdDifficulty = Math.sqrt(sdDifficulty / totalQuestions);

        averageTimePerQuestion = averageTimePerQuestion / totalQuestions;

        //System.out.println("\nThe average time per question is :  " + averageTimePerQuestion + "\n");

        long expectedQuestions = (long) (nrMinutesQuizz / averageTimePerQuestion);

        if( totalQuestions < expectedQuestions ) {
            // if we ask for more questions than we have in the database we return all the questions
            return Optional.of(listOfAllExistingQuestions(questionsById.get()));
        }

        //System.out.println("The expected number of questions is : " + expectedQuestions + "\n");


        List<Double> percentages = nrQuestionsPerLecture.stream()
                .map(nrQuestions -> (double) nrQuestions / totalQuestions)
                .toList(); //the lecture.get(0) will be chosen with percentages.get(0) probability

        //based on the questions difficulties we generate a gaussian distribution which will be used to find the questions
        Random random = new Random();

        List<Double> difficulties = new ArrayList<>();

        //generating a random gaussian distribution
        for( int i = 0 ; i < expectedQuestions ; ++i ){
            difficulties.add(random.nextGaussian() * sdDifficulty + meanDifficulty);
        }

//        System.out.println("\nThe distribution : \n");
//        for( double i : difficulties){
//            System.out.print(i + " ");
//        }

        //  after we have the gaussian distribution we uniformmly generate lectures (based on that percentage) from which we will get a question
        //  which has the difficulty closest to one of the difficulties D1 from the gaussian distribution;
        //  after we chose a question the D1 difficulty will not be considered any longer
        //  if no question has abs(it's difficulty, diff from gaussian) <= 0.5 --> we chose another lecture;
        //  if we chose 5 new lectures and no question was found to have to above property -> we increase the 0.5 by 0.5

        Random randomLecture = new Random();

        double currentTimeQuestions = 0;
        double difficultyDifference = 0.5;

        int nrTimesDifficultyDifferenceWasTooBig = 0;
        boolean lastTimeDifficultyDifferenceWasTooBig = false;

        Map <Long, Boolean > questionsAlreadyInserted = new HashMap<>();




        //Finding the Right Questions
        while ( currentTimeQuestions < nrMinutesQuizz ) {
            QuestionQuizzEntity questionQuizzEntity = this.getNextQuestion( randomLecture, questionsById.get(),
                                                                            percentages, difficulties,lectures,
                                                                            difficultyDifference,questionsAlreadyInserted);
            if( questionQuizzEntity == null ){

                if(questionsAlreadyInserted.size() == totalQuestions){//if we got all the questions from the database, but didn't reach a certain time -> we are done
                    break;
                }

                if( lastTimeDifficultyDifferenceWasTooBig ){

                    nrTimesDifficultyDifferenceWasTooBig++;

                    if( nrTimesDifficultyDifferenceWasTooBig == 3 ){
                        difficultyDifference += 0.5;
                        nrTimesDifficultyDifferenceWasTooBig = 0;
                        continue;
                    }
                }

                lastTimeDifficultyDifferenceWasTooBig = true;
                continue;
            }

            if(difficulties.size()==0){//regenerate a gaussian distribution
                for( int i = 0 ; i < expectedQuestions ; ++i ){
                    difficulties.add(random.nextGaussian() * sdDifficulty + meanDifficulty);
                }
                difficultyDifference = 0.5;
            }

            lastTimeDifficultyDifferenceWasTooBig = false;

            //if the current question has a time which would lead to a time of the quizz way bigger -> we skip it
            //ex : if the currTime = 8.9 and the QuizzTime = 10 and the question time = 10 -> the quizz time would be way too big, so we skip it and try o find another one
            if( currentTimeQuestions + questionQuizzEntity.getTimeMinutes() > nrMinutesQuizz + (1.5*averageTimePerQuestion) ){
                continue;
            }

            currentTimeQuestions += questionQuizzEntity.getTimeMinutes();

            listQuestionsQuizz.add(QuestionQuizzMapper.toDto(
                            questionQuizzEntity,
                            this.answerQuizzRepository.findByIdQuestion(questionQuizzEntity.getId()),
                            this.correctAnswerQuizzRepository.findByIdQuestion(questionQuizzEntity.getId())
                    )
            );

        }

        Collections.shuffle(listQuestionsQuizz);


        return Optional.of(listQuestionsQuizz);

    }

    private List<QuestionQuizzDto> listOfAllExistingQuestions(List<QuestionQuizzEntity> questionQuizzEntities) {

        List<QuestionQuizzDto> listQuestionsQuizz = new ArrayList<>();

        for( QuestionQuizzEntity questionQuizzEntity : questionQuizzEntities){
            listQuestionsQuizz.add(QuestionQuizzMapper.toDto(
                    questionQuizzEntity,
                    this.answerQuizzRepository.findByIdQuestion(questionQuizzEntity.getId()),
                    this.correctAnswerQuizzRepository.findByIdQuestion(questionQuizzEntity.getId())
                    )
            );
        }

        Collections.shuffle(listQuestionsQuizz);

        return listQuestionsQuizz;
    }

    private QuestionQuizzEntity getNextQuestion(Random randomLecture,
                                                List<QuestionQuizzEntity> questionsById,
                                                List<Double> percentages,
                                                List<Double> difficulties,
                                                List<Integer> lectures,
                                                double difficultyDifference,
                                                Map <Long, Boolean > questionsAlreadyInserted) {

        int nrLecture = this.lectureGenerator(randomLecture, lectures, percentages);

        for (QuestionQuizzEntity question : questionsById) {
            if( question.getLectureNumber() == nrLecture && !questionsAlreadyInserted.containsKey(question.getId())){

                double minDifference = 10;
                int indexMinDifference = -1;

                for( int i = 0 , n = difficulties.size() ; i < n ; ++i ){
                    double difference = Math.abs(question.getDifficulty() - difficulties.get(i));
                    if( difference < minDifference ){
                        minDifference = difference;
                        indexMinDifference = i;
                    }
                }
                //we remove from the gaussian distribution the closest difficulty to the question's difficulty
                if( minDifference <= difficultyDifference ){
                    difficulties.remove(indexMinDifference);
                    questionsAlreadyInserted.put(question.getId(), true);
                    return question;
                }

            }
        }

        return null;//if all the questions from the lecture have the difficulty too far from the gaussian distribution

    }


    private int lectureGenerator(Random randomLecture, List<Integer> lectures, List<Double> percentages) {

        double randomPercentage = randomLecture.nextDouble();

        for( int i = 0 , n = percentages.size() ; i < n && randomPercentage > 0 ; ++i ){
            randomPercentage -= percentages.get(i);
            if( randomPercentage <= 0 ){
                return lectures.get(i);
            }
        }

        return lectures.get(0);//will never get here

    }

}
