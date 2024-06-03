package nextstep.omok

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class GameResultDialog(
    private val gameResultDialogInterface: GameResultDialogInterface,
    private val text: String
) : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(
            R.layout.dialog_game_result,
            container,
            false
        )
        initiateListeners(view)
        setTitle(view)

        return view
    }

    private fun initiateListeners(view: View) {
        val newGameButton = view.findViewById<Button>(R.id.newgame_button)
        val continueButton = view.findViewById<Button>(R.id.continue_button)

        newGameButton.setOnClickListener {
            gameResultDialogInterface.onNewGameButtonClick()
            dismiss()
        }
        continueButton.setOnClickListener {
            gameResultDialogInterface.onContinueButtonClick()
            dismiss()
        }
    }

    private fun setTitle(view: View) {
        view.findViewById<TextView>(R.id.titleText).text = text
    }

}

interface GameResultDialogInterface {
    fun onNewGameButtonClick()
    fun onContinueButtonClick()
}