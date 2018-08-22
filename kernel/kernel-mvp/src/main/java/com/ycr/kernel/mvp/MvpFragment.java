package com.ycr.kernel.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ycr.kernel.mvp.connector.FragmentMvpConnector;
import com.ycr.kernel.mvp.view.IMvpView;

/**
 * Created by yuchengren on 2018/7/26.
 */
public class MvpFragment extends Fragment implements IMvpView {
	private FragmentMvpConnector mMvpConnector;

	@Override
	public FragmentMvpConnector getMvpConnector() {
		if(mMvpConnector == null){
			synchronized (this){
				if(mMvpConnector == null){
					mMvpConnector = new FragmentMvpConnector();
				}
			}
		}
		return mMvpConnector;
	}


	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		getMvpConnector().onAttach();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if(bundle == null){
			bundle = new Bundle();
		}
		getMvpConnector().onCreated(savedInstanceState,getActivity().getIntent(),bundle);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		getMvpConnector().onCreateView(savedInstanceState);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		getMvpConnector().onViewCreated(savedInstanceState);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getMvpConnector().onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
		getMvpConnector().onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		getMvpConnector().onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		getMvpConnector().onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
		getMvpConnector().onStop();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		getMvpConnector().onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getMvpConnector().onDestroy();
		getMvpConnector().destroyPresenter();
	}

	@Override
	public void onDetach() {
		super.onDetach();
		getMvpConnector().onDetach();
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		getMvpConnector().onSaveInstanceState(outState);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		getMvpConnector().onActivityResult(requestCode, resultCode, data);
	}
}
