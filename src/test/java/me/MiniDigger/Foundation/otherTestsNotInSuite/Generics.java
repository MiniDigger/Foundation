package me.MiniDigger.Foundation.otherTestsNotInSuite;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class Generics {
	public static void main(final String[] args) {
		// List<String> l = new ArrayList<String>(){};
		final List<String> l = new ArrayList<String>();
		l.add("tets");
		final Object obj = l;
		final List<Object> list = (List<Object>) obj;
		System.out.println(obj.getClass().getName());
		System.out.println(list.getClass().getName());
		System.out.println(((ParameterizedType) list.getClass().getGenericSuperclass()).getActualTypeArguments()[0].getClass().getName());
		System.out.println(((ParameterizedType) l.getClass().getGenericSuperclass()).getActualTypeArguments()[0].getClass().getName());
		System.out.println(net.jodah.typetools.TypeResolver.resolveGenericType(List.class, l.getClass().getGenericSuperclass()));
	}
}
