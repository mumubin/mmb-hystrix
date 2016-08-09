package config.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import config.dao.AdjectiveClient;
import config.dao.ArticleClient;
import config.dao.NounClient;
import config.dao.SubjectClient;
import config.dao.VerbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.Future;

@Service
public class WordServiceImpl implements WordService {

    @Autowired
    VerbClient verbClient;
    @Autowired
    SubjectClient subjectClient;
    @Autowired
    ArticleClient articleClient;
    @Autowired
    AdjectiveClient adjectiveClient;
    @Autowired
    NounClient nounClient;
    @Override
    @HystrixCommand(fallbackMethod = "getFallbackSubject")
    public Future<String> getSubject() {
        return new AsyncResult<String>() {
            @Override
            public String invoke() {
                return subjectClient.getWord();
            }
        };
    }

//    @Override
//    @HystrixCommand(fallbackMethod="getFallbackSubject")
//    public String getSubject() {
//        return subjectClient.getWord();
//    }



    @Override
    public String getVerb() {
        return verbClient.getWord();
    }

    @Override
    public String getArticle() {
        return articleClient.getWord();
    }

    @Override
    @HystrixCommand(fallbackMethod="getFallbackAdjective")
    public String getAdjective() {
        return adjectiveClient.getWord();
    }

    @Override
    @HystrixCommand(fallbackMethod="getFallbackNoun")
    public Observable<String> getNoun() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> observer) {
                try {
                    if (!observer.isUnsubscribed()) {
                        observer.onNext(nounClient.getWord());
                        observer.onCompleted();
                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        });
    }
//    public String getNoun() {
//        return nounClient.getWord();
//    }

    public String getFallbackSubject() {
        return "某人";
    }

    public String getFallbackAdjective() {
        return "";
    }

    public String getFallbackNoun() {
        return "某物";
    }
}
