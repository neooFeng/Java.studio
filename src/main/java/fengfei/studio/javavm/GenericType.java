package fengfei.studio.javavm;

// Code involving generic types is translated to code without generic types, using TransTypes, when Compilation.
class GenericType<T extends Number> {
    public double getValue(T customer) {
        return 0.0d;
    }
}