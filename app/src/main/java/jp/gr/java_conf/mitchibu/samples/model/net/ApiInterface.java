package jp.gr.java_conf.mitchibu.samples.model.net;

import java.util.List;

import io.reactivex.Single;
import jp.gr.java_conf.mitchibu.samples.model.entity.EntityA;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiInterface {
	@GET("/api/test")
	@Headers({
			"Content-Type: application/json"
	})
	Single<List<EntityA>> test();
}
