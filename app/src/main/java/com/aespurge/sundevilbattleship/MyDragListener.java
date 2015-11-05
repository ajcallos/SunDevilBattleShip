package com.aespurge.sundevilbattleship;

import android.view.DragEvent;
import android.view.View;

/**
 * Created by Anthony on 11/4/2015.
 */
public class MyDragListener implements View.OnDragListener {
    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                // do nothing
                break;
            case DragEvent.ACTION_DRAG_ENTERED:

                break;
            case DragEvent.ACTION_DRAG_EXITED:

                break;
            case DragEvent.ACTION_DROP:
                // Dropped, reassign View to ViewGroup

                break;
            case DragEvent.ACTION_DRAG_ENDED:

            default:
                break;
        }
        return true;
    }
}
