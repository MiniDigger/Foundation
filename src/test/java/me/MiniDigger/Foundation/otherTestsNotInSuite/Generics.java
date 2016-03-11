package me.MiniDigger.Foundation.otherTestsNotInSuite;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.reflections.Reflections;

public class Generics {
	public static void main(String[] args) {
		// List<String> l = new ArrayList<String>(){};
		List<String> l = new ArrayList<String>();
		l.add("tets");
		Object obj = l;
		List<Object> list = (List<Object>) obj;
		System.out.println(obj.getClass().getName());
		System.out.println(list.getClass().getName());
		System.out.println(((ParameterizedType) list.getClass().getGenericSuperclass()).getActualTypeArguments()[0]
				.getClass().getName());
		System.out.println(((ParameterizedType) l.getClass().getGenericSuperclass()).getActualTypeArguments()[0]
				.getClass().getName());
		System.out.println(net.jodah.typetools.TypeResolver.resolveGenericType(List.class, l.getClass().getGenericSuperclass()));
	}
}
