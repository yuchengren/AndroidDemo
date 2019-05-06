package com.ycr.kernel.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by yuchengren on 2018/7/27.
 */
public interface IFragmentSpecialLifeCycle {
	void onAttach();
	void onCreateView( @Nullable Bundle savedInstanceState);
	void onViewCreated(@Nullable Bundle savedInstanceState);
	void onActivityCreated(@Nullable Bundle savedInstanceState);
	void onDestroyView();
	void onDetach();
}
