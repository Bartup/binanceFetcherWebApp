package binance.webapp.binanceApp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KlineRepository extends JpaRepository<Kline, Long> {
}
