package com.lzy.commonsdk.views.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzy.commonsdk.R;


/**
 * 仿苹果dialog提示框
 * Created by zidan on 2017/11/1.
 */

public class CommonDialog extends Dialog {
    //一个按钮
    public static final int DIALOG_ONEOPTION = 1;
    //两个按钮
    public static final int DIALOG_TWOOPTION = -1;

    private TextView liftBtn, rightBtn;
    private TextView textText,dialogMsg;
    private String title;
    private String content;
    private String sureText;
    private String cancelText;
    private int type;
    private DialogListener dialogListener;


    /**
     *
     * @param activity
     * @param type
     *            DIALOG_ONEOPTION 表示一个按钮 DIALOG_TWOOPTION 表示两个按钮
     * @param title
     *            没有就传null
     * @param onclick
     *            一个按钮时 监听写在sure回调中 cancle不进行回调
     */

    public CommonDialog(Activity activity, int type, String title,
                        DialogListener onclick) {
        super(activity, R.style.CommonDialog);
        this.title = title;
        this.type = type;
        this.dialogListener = onclick;
    }


    /**
     *
     * @param activity
     * @param type
     *            DIALOG_ONEOPTION 表示一个按钮 DIALOG_TWOOPTION 表示两个按钮
     * @param title
     *            没有就传null
     * @param content
     *            没有就传null
     * @param onclick
     *            一个按钮时 监听写在sure回调中 cancle不进行回调
     */
    public CommonDialog(Activity activity, int type, String title, String content,
                        DialogListener onclick) {
        super(activity, R.style.CommonDialog);
        this.title = title;
        this.content = content;
        this.type = type;
        this.dialogListener = onclick;
    }


    /**
     *
     * @param activity
     * @param type
     *            DIALOG_ONEOPTION 表示一个按钮 DIALOG_TWOOPTION 表示两个按钮
     * @param title
     *            没有就传null
     * @param text
     *            没有就传null
     * @param sureText
     *            确定按钮文字
     * @param cancelText
     *            取消按钮文字
     * @param onclick
     *            一个按钮时 监听写在sure回调中 cancle不进行回调
     */
    public CommonDialog(Activity activity, int type, String title, String text,
                        String sureText, String cancelText,
                        DialogListener onclick) {
        super(activity, R.style.CommonDialog);
        this.title = title;
        this.content = text;
        this.type = type;
        this.sureText = sureText;
        this.cancelText = cancelText;
        this.dialogListener = onclick;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (type == DIALOG_ONEOPTION) {
            this.setContentView(R.layout.view_dialog_one);
        } else {
            this.setContentView(R.layout.view_dialog_two);
        }
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        initView();
        initAction();
    }

    private void initView() {
        textText = findViewById(R.id.title);
        liftBtn =  findViewById(R.id.dialog_yes);
        dialogMsg = findViewById(R.id.dialogMsg);


        if (TextUtils.isEmpty(title)) {
            textText.setVisibility(View.GONE);
        } else {
            textText.setText(title);
        }

        if (TextUtils.isEmpty(content)) {
            dialogMsg.setVisibility(View.GONE);
        } else {
            dialogMsg.setText(content);
        }

        if (!TextUtils.isEmpty(sureText)) {
            liftBtn.setText(sureText);
        }
        if (type != DIALOG_ONEOPTION) {
            rightBtn =  findViewById(R.id.dialog_no);
            if (!TextUtils.isEmpty(cancelText)) {
                rightBtn.setText(cancelText);
            }
        }


    }

    private void initAction() {
        if (type != DIALOG_ONEOPTION) {
            rightBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    dialogListener.cancleOnClick(v);
                }
            });
        }
        liftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                dialogListener.sureOnClick(v);
            }
        });
    }






}
