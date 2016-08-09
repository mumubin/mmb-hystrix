package config.service;

import com.google.common.collect.Lists;
import jdk.nashorn.internal.objects.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Build a sentence by assembling randomly generated subjects, verbs, 
 * articles, adjectives, and nouns.  The individual parts of speech will 
 * be obtained by calling the various DAOs.
 */
@Service
public class SentenceServiceImpl implements SentenceService {

	@Autowired WordService wordService;


	/**
	 * Assemble a sentence by gathering random words of each part of speech:
	 */
	public String buildSentence() {

		final List<String> nounList = Lists.newArrayList();
		wordService.getNoun().subscribe(new Subscriber<String>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(String s) {
				nounList.add(s);
			}
		});

		try {
			return String.format("%s %s %s %s %s.", wordService.getSubject().get(), wordService.getVerb(),
					wordService.getArticle(), wordService.getAdjective(), nounList.get(0));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return "error";
	}

}
