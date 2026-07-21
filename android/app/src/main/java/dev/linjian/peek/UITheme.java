package dev.linjian.peek;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

public class UITheme {
    public final String name;
    public final int bgTop, bgBottom, card, cardSoft, primary, primarySoft, accent, text, subtext, line, danger;
    public final boolean dark;

    private UITheme(String name, int bgTop, int bgBottom, int card, int cardSoft, int primary, int primarySoft, int accent, int text, int subtext, int line, int danger, boolean dark) {
        this.name = name; this.bgTop = bgTop; this.bgBottom = bgBottom; this.card = card; this.cardSoft = cardSoft;
        this.primary = primary; this.primarySoft = primarySoft; this.accent = accent; this.text = text; this.subtext = subtext;
        this.line = line; this.danger = danger; this.dark = dark;
    }

    public static UITheme current(Context ctx) {
        String n = AppPrefs.get(ctx).getString(AppPrefs.KEY_THEME, "奶油绿");
        return byName(n);
    }

    public static UITheme byName(String n) {
        if ("雾蓝白".equals(n)) return new UITheme("雾蓝白",
                Color.rgb(239, 247, 252), Color.rgb(250, 252, 255), Color.WHITE, Color.rgb(244, 249, 252),
                Color.rgb(112, 178, 198), Color.rgb(228, 244, 249), Color.rgb(190, 132, 160),
                Color.rgb(42, 52, 60), Color.rgb(111, 126, 135), Color.rgb(226, 235, 240), Color.rgb(226, 105, 122), false);
        if ("白桃粉".equals(n)) return new UITheme("白桃粉",
                Color.rgb(255, 250, 251), Color.rgb(255, 252, 250), Color.WHITE, Color.rgb(255, 247, 249),
                Color.rgb(219, 142, 162), Color.rgb(255, 235, 241), Color.rgb(119, 196, 184),
                Color.rgb(58, 47, 52), Color.rgb(139, 112, 123), Color.rgb(246, 219, 228), Color.rgb(225, 93, 112), false);
        if ("夜航黑".equals(n)) return new UITheme("夜航黑",
                Color.rgb(19, 24, 31), Color.rgb(28, 34, 42), Color.rgb(38, 45, 55), Color.rgb(46, 54, 65),
                Color.rgb(116, 188, 177), Color.rgb(50, 68, 75), Color.rgb(219, 145, 170),
                Color.rgb(239, 245, 242), Color.rgb(178, 194, 190), Color.rgb(63, 75, 86), Color.rgb(234, 112, 128), true);
        if ("薄荷透明".equals(n)) return new UITheme("薄荷透明",
                Color.rgb(238, 252, 248), Color.rgb(252, 255, 253), Color.argb(235, 255, 255, 255), Color.argb(210, 242, 251, 248),
                Color.rgb(100, 190, 172), Color.rgb(222, 248, 242), Color.rgb(244, 171, 184),
                Color.rgb(42, 62, 57), Color.rgb(104, 132, 125), Color.rgb(218, 241, 235), Color.rgb(225, 101, 118), false);
        return new UITheme("奶油绿",
                Color.rgb(248, 252, 249), Color.rgb(243, 250, 247), Color.WHITE, Color.rgb(247, 252, 249),
                Color.rgb(103, 181, 165), Color.rgb(229, 247, 242), Color.rgb(214, 158, 174),
                Color.rgb(43, 59, 54), Color.rgb(109, 134, 128), Color.rgb(226, 238, 234), Color.rgb(226, 105, 122), false);
    }

    public GradientDrawable background() {
        GradientDrawable g = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{bgTop, bgBottom});
        return g;
    }

    public GradientDrawable card(float radiusDp, float strokeDp) {
        GradientDrawable g = new GradientDrawable();
        g.setColor(card);
        g.setCornerRadius(dp(radiusDp));
        if (strokeDp > 0) g.setStroke((int) dp(strokeDp), line);
        return g;
    }

    public GradientDrawable soft(float radiusDp) {
        GradientDrawable g = new GradientDrawable();
        g.setColor(cardSoft);
        g.setCornerRadius(dp(radiusDp));
        g.setStroke((int) dp(0.6f), line);
        return g;
    }

    public GradientDrawable pill(boolean selected) {
        GradientDrawable g = new GradientDrawable();
        g.setColor(selected ? primary : (dark ? cardSoft : Color.argb(210, 255, 255, 255)));
        g.setCornerRadius(dp(15));
        g.setStroke((int) dp(0.6f), selected ? primary : line);
        return g;
    }

    public GradientDrawable chip(boolean selected) {
        GradientDrawable g = new GradientDrawable();
        g.setColor(selected ? primarySoft : card);
        g.setCornerRadius(dp(15));
        g.setStroke((int) dp(0.6f), selected ? primary : line);
        return g;
    }

    public static float dp(float v) { return v * android.content.res.Resources.getSystem().getDisplayMetrics().density; }
}
