package ke.co.debechlabs.besure.models;

/**
 * Created by Marewill on 6/9/2017.
 */

public class Faqs {
    private int _id, _faq_status;
    private String _faq_question, _faq_answer, _faq_imagepath;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_faq_question() {
        return _faq_question;
    }

    public void set_faq_question (String _faq_question) {
        this._faq_question = _faq_question;
    }

    public String get_faq_answer() {
        return _faq_answer;
    }

    public void set_faq_answer (String _faq_answer) {
        this._faq_answer = _faq_answer;
    }

    public String get_faq_imagepath() {
        return _faq_imagepath;
    }

    public void set_faq_imagepath (String _faq_imagepath) {
        this._faq_imagepath = _faq_imagepath;
    }

    public int get_faq_status() {
        return _faq_status;
    }

    public void set_faq_status (int _faq_status) {
        this._faq_status = _faq_status;
    }


}
