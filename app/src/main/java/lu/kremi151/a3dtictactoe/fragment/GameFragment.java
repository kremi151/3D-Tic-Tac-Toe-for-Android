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

package lu.kremi151.a3dtictactoe.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lu.kremi151.a3dtictactoe.R;
import lu.kremi151.a3dtictactoe.TTTApp;
import lu.kremi151.a3dtictactoe.interfaces.Acceptor;
import lu.kremi151.a3dtictactoe.interfaces.ActivityInterface;
import lu.kremi151.a3dtictactoe.interfaces.FieldColorInterceptor;
import lu.kremi151.a3dtictactoe.interfaces.ActivityAction;
import lu.kremi151.a3dtictactoe.interfaces.OnBoardTapListener;
import lu.kremi151.a3dtictactoe.interfaces.Savegame;
import lu.kremi151.a3dtictactoe.mode.GameMode;
import lu.kremi151.a3dtictactoe.mode.GameModeLocalMultiplayer;
import lu.kremi151.a3dtictactoe.mode.GameModeSingleplayer;
import lu.kremi151.a3dtictactoe.mode.GameModeTutorial;
import lu.kremi151.a3dtictactoe.util.AlertHelper;
import lu.kremi151.a3dtictactoe.util.GameCube;
import lu.kremi151.a3dtictactoe.view.GameBoard;

public class GameFragment extends BaseFragment{

    private GameBoard gameBoard;
    private GameCube cube = new GameCube();
    private GameMode gameMode;

    private List<ActivityAction> actions = new ArrayList<>();
    private boolean isInitialized = false;

    public GameFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            switch (getArguments().getString("gameMode", "multi_local")) {
                case "single":
                    gameMode = new GameModeSingleplayer(activityInterface, cube)
                            .setAttack(getArguments().getFloat("attack", 0.5f))
                            .setDefense(getArguments().getFloat("defense", 0.5f))
                            .setTag(getArguments().getString("tag", null))
                            .setOnWinListener(new Acceptor<GameModeSingleplayer>() {
                                @Override
                                public void onAccept(GameModeSingleplayer game) {
                                    if(game.getTag() != null){
                                        game.getSavegame().setMastered(game.getTag(), true);
                                    }
                                }
                            });
                    break;
                case "tutorial":
                    gameMode = new GameModeTutorial(activityInterface, cube);
                    break;
                default:
                    gameMode = new GameModeLocalMultiplayer(activityInterface, cube);
                    break;
            }
        }else{
            gameMode = new GameModeLocalMultiplayer(activityInterface, cube);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_game, container, false);
        this.gameBoard = root.findViewById(R.id.gameBoard);
        return root;
    }

    @Override
    public void onStart(){
        super.onStart();
        gameBoard.setCube(cube);
        gameBoard.setListener(listener);
        gameBoard.setFieldColorInterceptor(fieldColorInterceptor);
        gameBoard.setValueColorInterceptor(fieldValueColorInterceptor);
        gameBoard.resume();
        if(!isInitialized){
            gameMode.onInit();
            isInitialized = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        gameBoard.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        gameBoard.pause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        for(int i = 0 ; i < actions.size() ; i++){
            final ActivityAction action = actions.get(i);
            if(action.visible){
                MenuItem menuItem = menu.add(action.title)
                        .setOnMenuItemClickListener(action)
                        .setEnabled(action.enabled);
                menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:

                return true;
        }
        return false;
    }

    @Override
    public boolean onBackPressed(){
        if(!gameMode.isGameFinished()){
            AlertHelper.buildCloseGameAlert(getActivity(), new Runnable() {
                @Override
                public void run() {
                    getFragmentManager().popBackStack();
                }
            }).show();
            return true;
        }
        return super.onBackPressed();
    }

    private final OnBoardTapListener listener = new OnBoardTapListener() {
        @Override
        public boolean onTap(int x, int y, int z) {
            return !gameMode.isLocked() && gameMode.onTap(x, y, z);
        }
    };

    private final FieldColorInterceptor fieldColorInterceptor = new FieldColorInterceptor() {
        @Override
        public int getFieldColor(int x, int y, int z, int previousColor) {
            return gameMode.getFieldColor(x, y, z, previousColor);
        }
    };

    private final FieldColorInterceptor fieldValueColorInterceptor = new FieldColorInterceptor() {
        @Override
        public int getFieldColor(int x, int y, int z, int previousColor) {
            return gameMode.getFieldValueColor(x, y, z, previousColor);
        }
    };

    private final ActivityInterface activityInterface = new ActivityInterface() {
        @Override
        public void setSubtitle(int subtitle, Object... args) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(getString(subtitle, args));
        }

        @Override
        public void setSubtitle(CharSequence subtitle) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(subtitle);
        }

        @Override
        public ActionBuilder buildAction(int titleRes, Runnable action) {
            return new ActionBuilder(new ActivityAction(getString(titleRes), action)) {
                @Override
                public ActivityAction create() {
                    actions.add(action);
                    updateActions();
                    return action;
                }
            };
        }

        @Override
        public void updateActions() {
            getActivity().invalidateOptionsMenu();
        }

        @Override
        public Context getContext() {
            return GameFragment.this.getContext();
        }

        @Override
        public void updateBoard() {
            gameBoard.invalidate();
        }

        @Override
        public void enqueueTask(Runnable runnable) {
            getActivity().runOnUiThread(runnable);
        }

        @Override
        public Savegame getSavegame() {
            return ((TTTApp)getActivity().getApplication()).getSavegame();
        }
    };

    public static final GameFragment newTutorial(){
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putString("gameMode", "tutorial");
        fragment.setArguments(args);
        return fragment;
    }

    public static final GameFragment newLocalMultiplayer(){
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putString("gameMode", "multi_local");
        fragment.setArguments(args);
        return fragment;
    }

    public static final GameFragment newSingleplayer(float attack, float defense, String tag){
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putString("gameMode", "single");
        args.putFloat("attack", attack);
        args.putFloat("defense", defense);
        args.putString("tag", tag);
        fragment.setArguments(args);
        return fragment;
    }

}
