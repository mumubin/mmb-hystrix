package config.service;

import rx.Observable;

import java.util.concurrent.Future;

public interface WordService {

    Future<String> getSubject();

    String getVerb();

    String getArticle();

    String getAdjective();

    Observable<String> getNoun();

}
