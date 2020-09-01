package com.example.review;

import com.example.review.api.service.GithubService;
import com.example.review.utils.GithubServiceUtils;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Mock
    GithubService githubService;

    @Mock
    GithubService githubApiService;


    @Test
    public void getGithubServiceTest() {
        assertNotNull(GithubServiceUtils.getGithubService());
    }

    @Test
    public void getGithubApiService() {
        assertNotNull(GithubServiceUtils.getGithubApiService());
    }
    
}