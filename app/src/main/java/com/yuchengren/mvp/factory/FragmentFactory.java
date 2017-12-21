package com.yuchengren.mvp.factory;

import android.app.Fragment;

import com.yuchengren.mvp.app.ui.fragment.HomeFragment;
import com.yuchengren.mvp.app.ui.fragment.OtherFragment;
import com.yuchengren.mvp.constant.MenuCode;

import java.util.HashMap;

/**
 * Created by yuchengren on 2017/12/20.
 */

public class FragmentFactory {

	private static final String TAG = FragmentFactory.class.getName();

	private static FragmentFactory instance;

	private HashMap<String, Fragment> fragmentHashMap = new HashMap<>();


	private FragmentFactory(){

	}

	public static FragmentFactory getInstance(){
		if(instance == null){
			synchronized (FragmentFactory.class){
				if(instance == null){
					instance = new FragmentFactory();
				}
			}
		}
		return instance;
	}

	public Fragment getFragment(String tag){
		Fragment fragment;
		fragment = fragmentHashMap.get(tag);
		if(fragment == null){
			if(tag == null){
				return null;
			}
			switch (tag){
				case MenuCode.First.HOME:
					fragment = new HomeFragment();
					break;
				case MenuCode.First.OTHER:
					fragment = new OtherFragment();
					break;
				default:
					break;
			}
			fragmentHashMap.put(tag,fragment);
		}
		return fragment;
	}

	public void removeMainFragments(){
		fragmentHashMap.remove(MenuCode.First.HOME);
		fragmentHashMap.remove(MenuCode.First.OTHER);
	}

	public void removeFragment(String tag){
		fragmentHashMap.remove(tag);
	}


}
