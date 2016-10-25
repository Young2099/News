package com.demo.panguso.mvp_mode.listener;

/**
 * Created by ${yangfang} on 2016/10/20.
 */

public class ChannelItemMoveEvent {
    private int fromPosition;
    private int toPosition;

    public ChannelItemMoveEvent() {
    }

    public ChannelItemMoveEvent(int fromPosition, int toPosition) {
        this.fromPosition = fromPosition;
        this.toPosition = toPosition;
    }

    public int getFromPosition() {
        return fromPosition;
    }

    public int getToPosition() {
        return toPosition;
    }
}
