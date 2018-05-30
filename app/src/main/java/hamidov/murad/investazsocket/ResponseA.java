package hamidov.murad.investazsocket;

import java.util.ArrayList;
import java.util.List;

public class ResponseA {

    private List<Result> result = new ArrayList<Result>();

    private Integer total;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
