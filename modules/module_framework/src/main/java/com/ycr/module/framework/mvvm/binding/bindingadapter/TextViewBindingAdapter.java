package com.ycr.module.framework.mvvm.binding.bindingadapter;

import android.databinding.BindingAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.ycr.module.framework.mvvm.binding.command.BindingInCommand;

/**
 * created by yuchengren on 2019-06-05
 */
public class TextViewBindingAdapter {

    @BindingAdapter(value = {"beforeTextChangedCommand","onTextChangedCommand","afterTextChangedCommand"},requireAll = false)
    public static void addTextChangedListener(final TextView textView,
                                              final BindingInCommand<TextChangedWrapper> beforeTextChangedCommand,
                                              final BindingInCommand<TextChangedWrapper> onTextChangedCommand,
                                              final BindingInCommand<Editable> afterTextChangedCommand){
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(beforeTextChangedCommand != null){
                    beforeTextChangedCommand.execute(new TextChangedWrapper(s,start,after,count));
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(onTextChangedCommand != null){
                    onTextChangedCommand.execute(new TextChangedWrapper(s,start,before,count));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(onTextChangedCommand != null){
                    afterTextChangedCommand.execute(s);
                }
            }
        });
    }

    public static class TextChangedWrapper{
        public CharSequence text;
        public int start;
        public int beforeOrAfter;
        public int count;

        public TextChangedWrapper(CharSequence text, int start, int beforeOrAfter, int count){
            this.text = text;
            this.start = start;
            this.beforeOrAfter = beforeOrAfter;
            this.count = count;
        }
    }
}
