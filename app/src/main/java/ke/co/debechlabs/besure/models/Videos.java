package ke.co.debechlabs.besure.models;

/**
 * Created by chriz on 6/9/2017.
 */

public class Videos {

    private String _video_id, _video_title;

    private int _color;

    public String get_video_id() {
        return _video_id;
    }

    public void set_video_id(String _video_id) {
        this._video_id = _video_id;
    }


    public String get_video_title() {
        return _video_title;
    }

    public void set_video_title(String _video_title) {
        this._video_title = _video_title;
    }

    public int get_color() {
        return _color;
    }

    public void set_color(int _color) {
        this._color = _color;
    }
}
