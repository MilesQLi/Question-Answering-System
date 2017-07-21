package edu.pku.QRanking.summaryscore;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.pku.QRanking.Answer;
import edu.pku.QRanking.Question;

/**
 * Answer Extraction
 *
 * @author 李琦
 * @email stormier@126.com
 * 
 */
public class SkipBigramEmergeScorer implements SummaryScorer {

	public float weight;

	@Override
	public void score(Question question) {
		List<String> terms = new ArrayList();
		for (int j = 0; j < question.getTitle().size() - 2; j++) {
			if (question.getTagged_title().get(j).tag().equals("PU")
					|| question.getTagged_title().get(j).tag().equals("DT")
					|| question.getTagged_title().get(j).tag().equals("PN")
					|| question.getTagged_title().get(j).tag().equals("AD"))
				continue;
			if (question.getTagged_title().get(j + 2).tag().equals("PU")
					|| question.getTagged_title().get(j + 2).tag().equals("DT")
					|| question.getTagged_title().get(j + 2).tag().equals("PN")
					|| question.getTagged_title().get(j + 2).tag().equals("AD"))
				continue;
			if (question.getTitle().get(j).length() == 1
					|| question.getTitle().get(j + 2).length() == 1)
				continue;
			terms.add(question.getTitle().get(j) + ".{0,3}"
					+ question.getTitle().get(j + 2));
		}
		for (Answer answer : question.getAnswers()) {
			float score = 0;
			for (String term : terms) {
				Pattern p = Pattern.compile(term);
				String evidence_content_string = "";
				for (String word : answer.getSummary().getSummary_content()) {
					evidence_content_string += word;
				}
				Matcher matcher = p.matcher(evidence_content_string);
				while (matcher.find()) {
					String text = matcher.group();
					score += 2.0/answer.getSummary().getSummary_content().size();
				}
			}
			answer.getSummary().setScore(answer.getSummary().getScore() + weight*score);
	//		System.out.println("Evidence Skip Bigram Emerge score:" + answer.getAnswer_content()
   	//				+ " " + score);
		}

	}

}
