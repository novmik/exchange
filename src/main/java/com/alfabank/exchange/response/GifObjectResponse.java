package com.alfabank.exchange.response;

/**
 * GIF Objects are returned from most of GIPHY API's Endpoints.
 */
public class GifObjectResponse {

  private GifObject data;

  public GifObject getData() {
    return data;
  }
  public class GifObject {
    private String type;
    private String id;
    private String slug;
    private String url;

    private String bitly_url;

    private String embed_url;
    private String username;
    private String source;
    private String rating;
    private String content_url;
    //  private String user;
    private String source_tld;
    private String source_post_url;
    private String update_datetime;
    private String create_datetime;
    private String import_datetime;
    private String trending_datetime;
    //  private String images;
    private String title;

    public String getUrl() {
      return url;
    }

  }

}
