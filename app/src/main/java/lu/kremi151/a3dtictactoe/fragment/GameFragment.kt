/**
 * 3D Tic Tac Toe for Android
 * Copyright (C) 2018  Michel Kremer (kremi151)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package lu.kremi151.a3dtictactoe.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import lu.kremi151.a3dtictactoe.R
import lu.kremi151.a3dtictactoe.TTTApp
import lu.kremi151.a3dtictactoe.interfaces.*
import lu.kremi151.a3dtictactoe.interfaces.ActivityInterface.ActionBuilder
import lu.kremi151.a3dtictactoe.mode.GameMode
import lu.kremi151.a3dtictactoe.mode.GameModeLocalMultiplayer
import lu.kremi151.a3dtictactoe.mode.GameModeSingleplayer
import lu.kremi151.a3dtictactoe.mode.GameModeTutorial
import lu.kremi151.a3dtictactoe.util.AlertHelper.buildCloseGameAlert
import lu.kremi151.a3dtictactoe.util.GameCube
import lu.kremi151.a3dtictactoe.view.GameBoard
import java.util.*

class GameFragment : BaseFragment() {
    private var gameBoard: GameBoard? = null
    private val cube = GameCube()
    private var gameMode: GameMode? = null
    private val actions: MutableList<ActivityAction> = ArrayList()
    private var isInitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arguments = this.arguments;
        gameMode = if (arguments != null) {
            when (arguments.getString("gameMode", "multi_local")) {
                "single" -> GameModeSingleplayer(activityInterface, cube)
                        .setAttack(arguments.getFloat("attack", 0.5f))
                        .setDefense(arguments.getFloat("defense", 0.5f))
                        .setTag(arguments.getString("tag", null))
                        .setOnWinListener { game ->
                            if (game.tag != null) {
                                game.savegame?.setMastered(game.tag!!, true)
                            }
                        }
                "tutorial" -> GameModeTutorial(activityInterface, cube)
                else -> GameModeLocalMultiplayer(activityInterface, cube)
            }
        } else {
            GameModeLocalMultiplayer(activityInterface, cube)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_game, container, false)
        gameBoard = root.findViewById(R.id.gameBoard)
        return root
    }

    override fun onStart() {
        super.onStart()
        gameBoard!!.setCube(cube)
        gameBoard!!.setListener(listener)
        gameBoard!!.setFieldColorInterceptor(fieldColorInterceptor)
        gameBoard!!.setValueColorInterceptor(fieldValueColorInterceptor)
        gameBoard!!.resume()
        if (!isInitialized) {
            gameMode!!.onInit()
            isInitialized = true
        }
    }

    override fun onResume() {
        super.onResume()
        gameBoard!!.resume()
    }

    override fun onPause() {
        super.onPause()
        gameBoard!!.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as AppCompatActivity?)!!.supportActionBar!!.subtitle = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        for (i in actions.indices) {
            val action = actions[i]
            if (action!!.visible) {
                val menuItem = menu.add(action.title)
                        .setOnMenuItemClickListener(action)
                        .setEnabled(action.enabled)
                menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> return true
        }
        return false
    }

    override fun onBackPressed(): Boolean {
        if (!gameMode!!.isGameFinished) {
            buildCloseGameAlert(activity!!, Runnable { fragmentManager!!.popBackStack() }).show()
            return true
        }
        return super.onBackPressed()
    }

    private val listener: OnBoardTapListener = object : OnBoardTapListener {
        override fun onTap(x: Int, y: Int, z: Int): Boolean {
            return !gameMode!!.isLocked && gameMode!!.onTap(x, y, z)
        }
    }

    private val fieldColorInterceptor: FieldColorInterceptor = object : FieldColorInterceptor {
        override fun getFieldColor(x: Int, y: Int, z: Int, previousColor: Int): Int {
            return gameMode!!.getFieldColor(x, y, z, previousColor)
        }
    }

    private val fieldValueColorInterceptor: FieldColorInterceptor = object : FieldColorInterceptor {
        override fun getFieldColor(x: Int, y: Int, z: Int, previousColor: Int): Int {
            return gameMode!!.getFieldValueColor(x, y, z, previousColor)
        }
    }

    private val myContext: Context?
        get() = context

    private val activityInterface: ActivityInterface = object : ActivityInterface {
        override fun setSubtitle(subtitle: Int, vararg args: Any?) {
            (activity as AppCompatActivity?)?.supportActionBar?.subtitle = getString(subtitle, *args)
        }

        override fun setSubtitle(subtitle: CharSequence?) {
            (activity as AppCompatActivity?)?.supportActionBar?.subtitle = subtitle
        }

        override fun buildAction(titleRes: Int, runnable: Runnable): ActionBuilder? {
            return object : ActionBuilder(ActivityAction(getString(titleRes), runnable)) {
                override fun create(): ActivityAction? {
                    actions.add(action)
                    updateActions()
                    return action
                }
            }
        }

        override fun updateActions() {
            activity!!.invalidateOptionsMenu()
        }

        override val context: Context?
            get() = myContext

        override fun updateBoard() {
            gameBoard!!.invalidate()
        }

        override fun enqueueTask(runnable: Runnable?) {
            activity!!.runOnUiThread(runnable)
        }

        override val savegame: Savegame
            get() = (activity!!.application as TTTApp).savegame
    }

    companion object {
        @JvmStatic
        fun newTutorial(): GameFragment {
            val fragment = GameFragment()
            val args = Bundle()
            args.putString("gameMode", "tutorial")
            fragment.arguments = args
            return fragment
        }

        @JvmStatic
        fun newLocalMultiplayer(): GameFragment {
            val fragment = GameFragment()
            val args = Bundle()
            args.putString("gameMode", "multi_local")
            fragment.arguments = args
            return fragment
        }

        @JvmStatic
        fun newSingleplayer(attack: Float, defense: Float, tag: String?): GameFragment {
            val fragment = GameFragment()
            val args = Bundle()
            args.putString("gameMode", "single")
            args.putFloat("attack", attack)
            args.putFloat("defense", defense)
            args.putString("tag", tag)
            fragment.arguments = args
            return fragment
        }
    }
}