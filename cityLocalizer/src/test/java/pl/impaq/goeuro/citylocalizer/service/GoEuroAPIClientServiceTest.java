package pl.impaq.goeuro.citylocalizer.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import pl.impaq.goeuro.citylocalizer.exceptions.DataParseException;
import pl.impaq.goeuro.citylocalizer.exceptions.GoEuroAPIException;
import pl.impaq.goeuro.citylocalizer.json.Localization;
import pl.impaq.goeuro.citylocalizer.service.impl.GoEuroAPIClientServiceImpl;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;

/**
 * @author mmys
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(GoEuroAPIClientServiceImpl.class)
public class GoEuroAPIClientServiceTest {

   @Test
   public void testSingleObjectResponse() throws Exception {

      // given
      final String SINGLE_OBJECT_RESPONSE = "[{\"_id\":453202,\"key\":null,\"name\":\"Andrespol\",\"fullName\":\"Andrespol, Poland\",\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Poland\",\"geo_position\":{\"latitude\":51.72783,\"longitude\":19.64175},\"locationId\":153030,\"inEurope\":true,\"countryCode\":\"PL\",\"coreCountry\":true,\"distance\":null}]";
      HttpTransport transport = new MockHttpTransport() {
         @Override
         public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
            return new MockLowLevelHttpRequest() {
               @Override
               public LowLevelHttpResponse execute() throws IOException {
                  MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
                  result.setContentType(Json.MEDIA_TYPE);
                  result.setContent(SINGLE_OBJECT_RESPONSE);
                  return result;
               }
            };
         }
      };
      HttpRequestFactory rf = transport.createRequestFactory(new HttpRequestInitializer() {
         public void initialize(HttpRequest request) {
            request.setParser(new JsonObjectParser(new JacksonFactory()));
         }
      });

      GoEuroAPIClientServiceImpl serviceUnderTest = PowerMockito.spy(new GoEuroAPIClientServiceImpl());
      PowerMockito.doReturn(rf).when(serviceUnderTest, "createHttpRequestFactory");

      // when
      Localization[] localizations = serviceUnderTest.queryGoEuroAPIForCityLocalization("Andrespol");

      // then
      assertNotNull(localizations);
      assertEquals(1, localizations.length);
      assertEquals("Andrespol", localizations[0].getName());
   }

   @Test
   public void testEmptyResponse() throws Exception {

      // given
      final String EMPTY_RESPONSE = "[]";
      HttpTransport transport = new MockHttpTransport() {
         @Override
         public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
            return new MockLowLevelHttpRequest() {
               @Override
               public LowLevelHttpResponse execute() throws IOException {
                  MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
                  result.setContentType(Json.MEDIA_TYPE);
                  result.setContent(EMPTY_RESPONSE);
                  return result;
               }
            };
         }
      };
      HttpRequestFactory rf = transport.createRequestFactory(new HttpRequestInitializer() {
         public void initialize(HttpRequest request) {
            request.setParser(new JsonObjectParser(new JacksonFactory()));
         }
      });

      GoEuroAPIClientServiceImpl serviceUnderTest = PowerMockito.spy(new GoEuroAPIClientServiceImpl());
      PowerMockito.doReturn(rf).when(serviceUnderTest, "createHttpRequestFactory");

      // when
      Localization[] localizations = serviceUnderTest.queryGoEuroAPIForCityLocalization("xyz");

      // then
      assertNotNull(localizations);
      assertEquals(0, localizations.length);
   }

   @Test(expected = GoEuroAPIException.class)
   public void test400Response() throws Exception {

      // given
      HttpTransport transport = new MockHttpTransport() {
         @Override
         public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
            return new MockLowLevelHttpRequest() {
               @Override
               public LowLevelHttpResponse execute() throws IOException {
                  MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
                  result.setStatusCode(400);
                  return result;
               }
            };
         }
      };
      HttpRequestFactory rf = transport.createRequestFactory(new HttpRequestInitializer() {
         public void initialize(HttpRequest request) {
            request.setParser(new JsonObjectParser(new JacksonFactory()));
         }
      });

      GoEuroAPIClientServiceImpl serviceUnderTest = PowerMockito.spy(new GoEuroAPIClientServiceImpl());
      PowerMockito.doReturn(rf).when(serviceUnderTest, "createHttpRequestFactory");

      // when
      serviceUnderTest.queryGoEuroAPIForCityLocalization("xyz");

      // then
      // GoEuroAPIException should be thrown
   }

   @Test(expected = DataParseException.class)
   public void testMalformedResponse() throws Exception {

      // given
      final String MALFORMED_RESPONSE = "[dfhbiucvsnbd";
      HttpTransport transport = new MockHttpTransport() {
         @Override
         public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
            return new MockLowLevelHttpRequest() {
               @Override
               public LowLevelHttpResponse execute() throws IOException {
                  MockLowLevelHttpResponse result = new MockLowLevelHttpResponse();
                  result.setContentType(Json.MEDIA_TYPE);
                  result.setContent(MALFORMED_RESPONSE);
                  return result;
               }
            };
         }
      };
      HttpRequestFactory rf = transport.createRequestFactory(new HttpRequestInitializer() {
         public void initialize(HttpRequest request) {
            request.setParser(new JsonObjectParser(new JacksonFactory()));
         }
      });

      GoEuroAPIClientServiceImpl serviceUnderTest = PowerMockito.spy(new GoEuroAPIClientServiceImpl());
      PowerMockito.doReturn(rf).when(serviceUnderTest, "createHttpRequestFactory");

      // when
      serviceUnderTest.queryGoEuroAPIForCityLocalization("xyz");

      // then
      // DataParseException should be thrown
   }

}
