package binance.webapp.binanceApp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Kline {

    @Id
    @GeneratedValue
    private Long id;
    private String pair;
    public Date openingDate;
    public Date lowestDate;
    public float low;
    public float high;
    public float current;
    public float open;
    public float growth;
    public float fall;

    public Kline() {

    }

    public Kline(String pair, Date openingDate, float low, float high, float current, float open, Date lowestDate) {
        this.pair = pair;
        this.openingDate = openingDate;
        this.low = low;
        this.high = high;
        this.current = current;
        this.open = open;
        this.growth = current/low * 100;
        this.fall = current/high * 100;
        this.lowestDate = lowestDate;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public Date getLowestDate() {
        return lowestDate;
    }

    public void setLowestDate(Date lowestDate) {
        this.lowestDate = lowestDate;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getCurrent() {
        return current;
    }

    public void setCurrent(float current) {
        this.current = current;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getGrowth() {
        return growth;
    }

    public void setGrowth(float growth) {
        this.growth = growth;
    }

    public float getFall() {
        return fall;
    }

    public void setFall(float fall) {
        this.fall = fall;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
