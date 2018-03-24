Simple Instance:

```
new RequestClient.Builder()
      .url("http://www.kuaidi100.com/query")
      .param("type","yuantong")
      .param("postid","500379523313")
      .builder()
      .post(PostQueryResponse.class, new OnResponseListener<PostQueryResponse>() {
          @Override
          public void onSuccess(PostQueryResponse content) {
              Toast.makeText(getApplicationContext(),content.toString(),Toast.LENGTH_LONG).show();
          }

          @Override
          public void onFailure(Throwable err) {

          }
      });
```
