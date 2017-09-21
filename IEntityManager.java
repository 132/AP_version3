public interface IEntityManager<T>{
	void persist(T entity);
	void remove(T entity);
	T find(Object primaryKey);
	Query<T> createQuery(String query);

}
