package com.massisframework.sweethome3d.plugins.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.massisframework.sweethome3d.plugins.components.properties.MassisProperty;

public class HomeObjectComponents implements List<HomeObjectComponent>,MassisProperty {
	
	private List<HomeObjectComponent> inner = new ArrayList<>();

	@Override
	public int size()
	{
		return this.inner.size();
	}

	@Override
	public boolean isEmpty()
	{
		return this.inner.isEmpty();
	}

	@Override
	public boolean contains(Object o)
	{
		return this.inner.contains(o);
	}

	@Override
	public Iterator<HomeObjectComponent> iterator()
	{
		return this.inner.iterator();
	}

	@Override
	public Object[] toArray()
	{
		return this.inner.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a)
	{
		return this.inner.toArray(a);
	}

	@Override
	public boolean add(HomeObjectComponent e)
	{
		return this.inner.add(e);
	}

	@Override
	public boolean remove(Object o)
	{
		return this.inner.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		return this.inner.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends HomeObjectComponent> c)
	{
		return this.inner.addAll(c);
	}

	@Override
	public boolean addAll(int index,
			Collection<? extends HomeObjectComponent> c)
	{
		return this.inner.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		return this.inner.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		return this.inner.retainAll(c);
	}

	@Override
	public void clear()
	{
		this.inner.clear();

	}

	@Override
	public HomeObjectComponent get(int index)
	{
		return this.inner.get(index);
	}

	@Override
	public HomeObjectComponent set(int index, HomeObjectComponent element)
	{
		return this.inner.set(index, element);
	}

	@Override
	public void add(int index, HomeObjectComponent element)
	{
		this.inner.add(index, element);
	}

	@Override
	public HomeObjectComponent remove(int index)
	{
		return this.inner.remove(index);
	}

	@Override
	public int indexOf(Object o)
	{
		return this.inner.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o)
	{
		return this.inner.lastIndexOf(o);
	}

	@Override
	public ListIterator<HomeObjectComponent> listIterator()
	{
		return this.inner.listIterator();
	}

	@Override
	public ListIterator<HomeObjectComponent> listIterator(int index)
	{
		return this.inner.listIterator(index);
	}

	@Override
	public List<HomeObjectComponent> subList(int fromIndex, int toIndex)
	{
		return this.inner.subList(fromIndex, toIndex);
	}
}
