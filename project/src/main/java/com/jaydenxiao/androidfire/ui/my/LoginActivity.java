package com.jaydenxiao.androidfire.ui.my;

import android.app.Activity;
import android.content.Intent;

import com.commonview.anim.rotate.EasyFlipView;
import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.common.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity {


    @Bind(R.id.easyFlipView)
    EasyFlipView easyFlipView;

    @Override
    public int getLayoutId() {
        return R.layout.login_register;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
    }

    /**
     * 入口
     * @param activity
     */
    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
//        activity.overridePendingTransition(R.anim.fade_in,
//                com.jaydenxiao.common.R.anim.fade_out);
    }

    @OnClick(R.id.id_login)
    public void toRegister(){
        easyFlipView.flipTheView();
    }
    @OnClick(R.id.id_register)
    public void toLogin(){
        easyFlipView.flipTheView();
    }
}
