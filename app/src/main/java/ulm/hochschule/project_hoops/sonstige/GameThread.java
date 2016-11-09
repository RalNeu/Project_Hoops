package ulm.hochschule.project_hoops.sonstige;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import ulm.hochschule.project_hoops.views.GamePanel;

/**
 * Created by Johann on 19.09.2016.
 */
public class GameThread extends Thread {

    private SurfaceHolder holder;
    private GamePanel gamePanel;
    private GameModel model;
    private boolean running;

    public GameThread(SurfaceHolder holder, GamePanel gamePanel, GameModel model){
        super();
        this.holder = holder;
        this.gamePanel = gamePanel;
        this.model = model;
    }

    @Override
    public void run() {
        Canvas c;
        while (running) {
            gamePanel.update();
            model.update();
            c = null;
            try {
                c = holder.lockCanvas(null);
                synchronized (holder) {
                    gamePanel.draw(c);
                }
            } finally {
                if (c != null) {
                    holder.unlockCanvasAndPost(c);
                }
            }
        }
    }

    public void setRunning(boolean running){
        this.running = running;
    }
}
