package com.bit2016.myapp.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bit2016.android.network.SafeAsyncTask;
import com.bit2016.myapp.R;
import com.bit2016.myapp.core.domain.User;
import com.bit2016.myapp.core.service.UserService;

import java.util.List;

/**
 * Created by bit-user on 2016-12-02.
 */

public class UserListFragment extends ListFragment {
    private UserListArrayAdapter userListArrayAdapter;
    private UserService userService = new UserService();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userListArrayAdapter = new UserListArrayAdapter(getActivity());
        setListAdapter(userListArrayAdapter);

        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //통신 시작
        new FetchUserListAsyncTask().execute();
    }

    /*
               서버로 부터 User List 를 가져오는 Ajax 비동기 통신을 하는 AsyncTask클래스
         */
    private class FetchUserListAsyncTask extends SafeAsyncTask<List<User>>{

        @Override
        public List<User> call() throws Exception {
            /* 실제 네트워크 통신 코드 작성 */
            /* 통신은 내부적으로 쓰레드 기반의 비동기 통신 */
            getActivity().findViewById(R.id.progress).setVisibility(View.VISIBLE);
            List<User> list = userService.fetchUserList();
            return list;
        }

        @Override
        protected void onException(Exception e) throws RuntimeException {
            throw new RuntimeException(e);
        }

        @Override
        protected void onSuccess(List<User> users) throws Exception {
            // 통신결과를 처리
            userListArrayAdapter.add(users);
            getActivity().findViewById(R.id.progress).setVisibility(View.GONE);
        }
    }
}


