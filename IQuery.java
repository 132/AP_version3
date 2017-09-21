import java.util.List;

interface IQuery<T>{
	List<T> getResultList();
	void execute();
}