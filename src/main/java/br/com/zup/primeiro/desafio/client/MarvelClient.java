package br.com.zup.primeiro.desafio.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.zup.primeiro.desafio.controller.response.marvel.MarvelResponse;

@FeignClient(name = "marvel", url = "${url.marvel}/v1/public")
public interface MarvelClient {
	@GetMapping("/comics")
	public MarvelResponse getAll(@RequestParam(value = "ts") Long timeStamp,
			@RequestParam(value = "apikey") String publicKey, @RequestParam(value = "hash") String hashMD5);
}
