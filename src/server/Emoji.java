package server;

import java.util.HashMap;
import java.util.Map;

/*
* Le string di java utilizzano un encoding utf-16, e supportano le emoji nativamente.
* https://unicode.org/emoji/charts/full-emoji-list.html
* */
public interface Emoji{
	HashMap<String,String> REPLACE_MAP = new HashMap<>(){{
		/*Ogni carattere in utf-16 appartiene ad un certo "plane", tutti i caratteri piu usati in occidente appartengono
		* al bmp(basic multilingual plane) e sono gli unici rappresentabili con 16 bit.
		* Tutti gli altri (comprese la maggior parte delle emoji) fanno parte di altri plane supplementari e vengono
		* rappresentate con 32 bit, questi bits sono divisi in due gruppi da 16 :
		* il primo chiamato high surrogate e il secondo low surrogate (es. \uD83D(high)\uDE0A(low) che rappresenta 😊,
		* la u indica unicode)
		* */
		put(":-)", "\uD83D\uDE0A");
		put(":)", "\uD83D\uDE0A");
		put(":-(", "\uD83D\uDE1E");
		put(":(", "\uD83D\uDE1E");
		put(":-D", "\uD83D\uDE03");
		put(":D", "\uD83D\uDE03");
		put(";-)", "\uD83D\uDE09");
		put(";)", "\uD83D\uDE09");
		put(":-P", "\uD83D\uDE1C");
		put(":P", "\uD83D\uDE1C");
		put(":-*", "\uD83D\uDE18");
		put(":*", "\uD83D\uDE18");
		put("<3", "❤"); //appartiene al bmp
		put("=O", "\uD83D\uDE31");
		put("xD", "\uD83D\uDE01");
		put(":')", "\uD83D\uDE02");
		put(":-/", "\uD83D\uDE12");
		put(":/", "\uD83D\uDE12");
		put(":-|", "\uD83D\uDE14");
		put(":|", "\uD83D\uDE14");
		put("*_*", "\uD83D\uDE0D");
	}};

	static String checkForEmojis(String in){

		for (Map.Entry<String, String> entry : REPLACE_MAP.entrySet()) {
			in = in.replace(entry.getKey(), entry.getValue());
		}
		return in;
	}
}
