package az.company.elibrary.security.service;

public interface TokenReader<T> {

    T read(String token);

}
