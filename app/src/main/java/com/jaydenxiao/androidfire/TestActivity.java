package com.jaydenxiao.androidfire;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/6/27 0027.
 */
public class TestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        Button btn = (Button) this.findViewById(R.id.id_click);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxjava2();
            }
        });
    }

    private void rxjava_create(){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("s");
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String o) {
                Toast.makeText(getApplicationContext(),o,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void rxjava2(){
        //subscriber是观察者，订阅Observable后，如果没有取消订阅，Observable会调用subscriber的方法实现观察者模式
        //首先回调onNext，最后是onCompleted
        //如果中间抛出异常，会停止传递，回调onError，这里理解起来还是很简单的。
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Toast.makeText(TestActivity.this,"completed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(TestActivity.this,"error",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Integer value) {
                Toast.makeText(TestActivity.this,""+value,Toast.LENGTH_SHORT).show();
            }

        };
        //RxJava提供了很多创建Observable的方法，比如from将一个Iterable, 一个Future, 或者一个数组转换成一个Observable
        //repeat  创建一个重复发射指定数据或数据序列的Observable
        //repeatWhen 创建一个重复发射指定数据或数据序列的Observable，它依赖于另一个Observable发射的数据
        //这里是最简单的just方法，其内部实现是create方法.
        Observable.just("1", "2", "3")
                //map方法是最基本的操作符方法，用来实现业务逻辑要求的参数的变化，也是RxJava的一个核心，其内部实现是lift方法，下文会提及
                //这里是将String类型转为整形，具体的变换逻辑需要重写其中的call方法
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String value) {
                        return Integer.valueOf(value);
                    }
                })
                //observeOn设定subscriber的回调操作执行在UI线程，这里就涉及到了RxJava的另一个核心"异步"
                //其内部实现同样是lift方法，subscribeOn则是设定业务执行逻辑执行在IO线程
                //subscribeOn的内部实现和observeOn是差不多的
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                //订阅Observable(显然这里改成subscriber.subscribe(observable)更好理解)，之后会执行Subscriber的回调
                .subscribe(subscriber);
    }
}
