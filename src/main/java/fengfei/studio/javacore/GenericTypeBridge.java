package fengfei.studio.javacore;

class GenericTypeBridge extends GenericType<Integer> {
    @Override
    public double getValue(Integer value) {
        return 0.0d;
    }
}