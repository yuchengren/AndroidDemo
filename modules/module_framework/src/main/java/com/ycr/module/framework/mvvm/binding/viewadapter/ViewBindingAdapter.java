package com.ycr.module.framework.mvvm.binding.viewadapter;

import android.databinding.BindingAdapter;
import android.view.View;

import com.ycr.module.framework.mvvm.binding.command.BindingCommand;
import com.ycr.module.framework.mvvm.binding.command.BindingInCommand;

/**
 * created by yuchengren on 2019-06-04
 */
public class ViewBindingAdapter {

    @BindingAdapter(value = {"onClickCommand"},requireAll = false)
    public static void onClickCommand(View view, final BindingCommand command){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(command != null){
                    command.execute();
                }
            }
        });
    }

    @BindingAdapter(value = {"onFocusChangeCommand"},requireAll = false)
    public static void onFocusChangeCommand(View view, final BindingInCommand<Boolean> command){
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(command != null){
                    command.execute(hasFocus);
                }
            }
        });
    }
}
