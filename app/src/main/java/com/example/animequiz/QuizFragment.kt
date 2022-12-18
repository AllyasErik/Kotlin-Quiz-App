package com.example.animequiz


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.animequiz.databinding.FragmentGameBinding
import kotlinx.android.synthetic.main.fragment_game.*


/**
 * A simple [Fragment] subclass.
 */
class QuizFragment : Fragment() {
    lateinit var binding: FragmentGameBinding
    lateinit var currentQuestion:Question
    private var questionIndex = 0
    private val maxNumberOfQuestion = 7

    var questions:ArrayList<Question> = arrayListOf(
        Question("What is Naruto's family name?",
            arrayListOf("Uzumaki","Uchiha","Namikaze","Senju") ),
        Question("Which one is not a dōjutsu in Naruto?",
            arrayListOf("Amaterasu","Sharingan", "Rinnegan", "Byakugan")),
        Question("What is Tanjiro's sister's name in Demon Slayer?",
            arrayListOf("Nezuko","Kanao","Mitsuri","Shinobu")),
        Question("How do the captains from Bleach call their strongest move?",
            arrayListOf("Ban-kai","Jutsu","Special move","Ultimate technique")),
        Question("What did the other players call Kirito in the  first season of Sword Art Online?",
            arrayListOf("Beater","Swordsman","Tester","Cheater")),
        Question("In My Hero Academia which character has both ice and fire as a power?",
            arrayListOf("Todoroki","All Might","Endeavor","Dabi")),
        Question("In Attack On Titen what titen is originally given to Eren?",
            arrayListOf("Attack titan","Colossal titan","Armored titan","Warhammer titan")),
        Question("In what team did Kageyama play before entering Karasuno in Haikyuu?",
            arrayListOf("Aoba Johsai","Nekoma","Shiratorizawa","Inarizaki")),
    )

    lateinit var answers:ArrayList<String>
    lateinit var selectedAnswer:String
    var score:Int = 0
    var wrongAnswerList:ArrayList<String> = ArrayList()

    lateinit var viewModel: QuizViewModel

    private fun setQuestion(){
        currentQuestion = questions[questionIndex]
        answers = ArrayList(currentQuestion.answerGroup)
        answers.shuffle()

        Log.d("ANSWERGROUP", answers[0]+ " "+answers[1]+ " "+answers[2]+ " "+answers[3]+ " ")
        Log.d("REALANSWER", currentQuestion.answerGroup[0])



    }

    private fun randomQuestion(){
        questions.shuffle()
        setQuestion()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_game,container,false)
        viewModel = ViewModelProvider(this).get(QuizViewModel::class.java)
        binding.quizViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        randomQuestion()
        binding.game=this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        option1.setOnClickListener{
            checkAnswer(option1.text.toString())
        }
        option2.setOnClickListener{
            checkAnswer(option2.text.toString())
        }
        option3.setOnClickListener{
            checkAnswer(option3.text.toString())
        }
        option4.setOnClickListener{
            checkAnswer(option4.text.toString())
        }

    }

    private fun checkAnswer(answer:String){
        if(answer.equals(currentQuestion.answerGroup[0])){
            score+=1
        }
        else{
            wrongAnswerList.add(currentQuestion.theQuestion)
        }
        questionIndex++
        if(questionIndex<=maxNumberOfQuestion){
            setQuestion()
            binding.invalidateAll()
        }
        else{
            getScore();
        }
    }

    private fun getScore(){
        if(score>=4){
            //Navigation.findNavController(view!!).navigate(R.id.action_quizFragment_to_gameWonFragment);
            view!!.findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToGameWonFragment(score))

        }
        else{
            view!!.findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToGameOverFragment(score))
        }
    }

}
