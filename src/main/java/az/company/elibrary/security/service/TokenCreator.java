package az.company.elibrary.security.service;

public interface TokenCreator<T> {

    String create(T t);

}
