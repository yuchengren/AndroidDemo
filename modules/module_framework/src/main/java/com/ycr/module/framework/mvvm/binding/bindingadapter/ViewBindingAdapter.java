package com.ycr.module.framework.mvvm.binding.bindingadapter;

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

    @BindingAdapter(value = {"onLongClickCommand"},requireAll = false)
    public static void onLongClickCommand(View view, final BindingCommand command){
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

    @BindingAdapter(value = {"android:onFocusChange"},requireAll = false)
    public static void onFocusChange(View view, final View.OnFocusChangeListener listener){
        if(view.getOnFocusChangeListener() != listener){
            view.setOnFocusChangeListener(listener);
        }
    }

    @BindingAdapter(value = {"isVisible"}, requireAll = false)
    public static void isVisible(View view, final Boolean visibility) {
        if (visibility) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    @BindingAdapter(value = {"isGone"}, requireAll = false)
    public static void isGone(View view, final Boolean visibility) {
        if (visibility) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }


}
