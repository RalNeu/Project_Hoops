package ulm.hochschule.project_hoops.sonstige;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import ulm.hochschule.project_hoops.Views.GamePanel;

/**
 * Created by Johann on 19.09.2016.
 */
public class GameThread extends Thread {

    private SurfaceHolder holder;
    private GamePanel gamePanel;
    private boolean running;

    public GameThread(SurfaceHolder holder, GamePanel gamePanel){
        super();
        this.holder = holder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        Canvas c;
        while (running) {
            gamePanel.update();
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
