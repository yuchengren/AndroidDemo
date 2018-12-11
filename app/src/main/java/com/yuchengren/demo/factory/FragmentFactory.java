package com.yuchengren.demo.factory;

import android.app.Fragment;

import com.yuchengren.demo.app.body.main.fragment.HomeFragment;
import com.yuchengren.demo.app.body.main.fragment.OtherFragment;
import com.yuchengren.demo.constant.MenuCode;

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
