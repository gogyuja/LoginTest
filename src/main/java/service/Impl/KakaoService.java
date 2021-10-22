package service.Impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class KakaoService {
//전체 참조 예제 : https://mylupin.tistory.com/48 
	
	public String getAccessToken(String authorize_code) {
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(reqURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// POST 요청을 위해 기본값이 false인 setDoOutput을 true로

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=bd2bfec7dc12e96624279aa82860fbca"); // 본인이 발급받은 key
			sb.append("&redirect_uri=http://localhost:8080/kakao/login"); // 본인이 설정해 놓은 경로
			sb.append("&code=" + authorize_code);
			bw.write(sb.toString());
			bw.flush();

			// 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			// Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			access_Token = element.getAsJsonObject().get("access_token").getAsString();
			refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

			System.out.println("access_token : " + access_Token);
			System.out.println("refresh_token : " + refresh_Token);

			br.close();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return access_Token;
	}
	
	 public HashMap<String, Object> getUserInfo (String access_Token) {

         //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
         HashMap<String, Object> userInfo = new HashMap<>();
         String reqURL = "https://kapi.kakao.com/v2/user/me";
         try {
             URL url = new URL(reqURL);
             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
             conn.setRequestMethod("GET");

             //    요청에 필요한 Header에 포함될 내용
             conn.setRequestProperty("Authorization", "Bearer " + access_Token);

             int responseCode = conn.getResponseCode();
             System.out.println("responseCode : " + responseCode);

             BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

             String line = "";
             String result = "";

             while ((line = br.readLine()) != null) {
                 result += line;
             }
             System.out.println("response body : " + result);

             JsonParser parser = new JsonParser();
             JsonElement element = parser.parse(result);

             JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
             JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
             
             //회원의 id 이 값이 소셜로그인에서 아이디 대용으로 사용된다.
             String id=element.getAsJsonObject().get("id").getAsString();
             userInfo.put("id",id);
             
             //필수동의
             String nickname = properties.getAsJsonObject().get("nickname").getAsString();
             String profile_image = properties.getAsJsonObject().get("profile_image").getAsString();
             
             //필수동의
             userInfo.put("nickname", nickname);
             userInfo.put("profile_image", profile_image);
             
             //사용자가 선택동의를 하지않을 경우 nullpoint 에러가 난다.
             //이번 프로젝트에서는 소셜로그인을 단순히 로그인(아이디&pw)대용으로 써서 밑 정보들은 필요하지않다.
             //그냥 들고올수있나 없나 궁금해서 써봤다.
             //선택동의
             String email = kakao_account.getAsJsonObject().get("email").getAsString();
             String gender=kakao_account.getAsJsonObject().get("gender").getAsString();
             String birthday_type=kakao_account.getAsJsonObject().get("birthday_type").getAsString();
             String birthday=kakao_account.getAsJsonObject().get("birthday").getAsString();
             String age_range=kakao_account.getAsJsonObject().get("age_range").getAsString();           
             //선택동의. 
             userInfo.put("email", email);
             userInfo.put("gender",gender);
             userInfo.put("birthday_type",birthday_type);
             userInfo.put("birthday",birthday);
             userInfo.put("age_range", age_range);
             
         }catch(NullPointerException e) {
        	 //사용자가 선택동의의 일부를 선택하지 않았을 경우 kakao_account에서 뺄 자료가 없어 에러가난다.
         }
         catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }

         return userInfo;
     }
}