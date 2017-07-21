package edu.pku.QRanking.answerscore;

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

public class TermAlignmentScorer implements AnswerScorer{

	float weight;
	
	@Override
	public void score(Question question) {
		for (Answer answer : question.getAnswers()) {
			
			for (int i = 0; i < question.getTitle().size(); i++) {
				List<String> origin_pattern = new ArrayList();
				for (int j = 0; j < question.getTitle().size(); j++) {
					
					if(question.getTagged_title().get(j).tag().equals("PU"))
						continue;
					
                    if (i == j) {
                    	origin_pattern.add(answer.getAnswer_content());
                    } else {
                    	origin_pattern.add(question.getTitle().get(j));
                    }
				}
				String origin_pattern_string = "";
				for (String word : origin_pattern) {
					origin_pattern_string += word;
				}
				//System.out.println("origin pattern:"+origin_pattern_string);
				
                List<String> patterns = new ArrayList<String>();
           //     patterns.add(origin_pattern_string);
                StringBuilder str = new StringBuilder();
                for (int t = 0; t < origin_pattern.size(); t++) {
                    str.append(origin_pattern.get(t));
                    if (t < origin_pattern.size() - 1) {
                        str.append(".{0,20}");
                    }
                }
                patterns.add(str.toString());
                //5、判断文本是否对齐
                int count = 0;
                int length = 0;
                for (String pattern : patterns) {
                    //LOG.debug("模式："+pattern);
               // 	System.out.println("pattern:"+pattern);
                	try
                	{
                    Pattern p = Pattern.compile(pattern);
                    
                    String evidence_content_string = "";
    				for (String word : answer.getSummary().getSummary_content()) {
    					evidence_content_string += word;
    				}
                    
                    Matcher matcher = p.matcher(evidence_content_string);
                    while (matcher.find()) {
                        String text = matcher.group();
           //             System.out.println("mached pattern:"+pattern+" text:"+text);
                        count++;
                        length += text.length();
                    }
                	}catch(Exception e)
                	{
                		continue;
                	}
                	
                }
				
				
                if (count > 0) {
                   float avgLen = length /  (float)count;
                   int questionLen = question.getTitle().size();
                   float score = weight * questionLen / avgLen;
                   answer.setScore(answer.getScore() + score);
      //             System.out.println("term alignment score:" + answer.getAnswer_content()
      // 					+ " " + score + " count:" + count+" length:"+length);
                }
				
				
			}
		}
		
	}

}
