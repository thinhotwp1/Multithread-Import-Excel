package learn.ldt.importexcelmultithread.controller;

import learn.ldt.importexcelmultithread.service.AnyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AnyController {

    @Autowired
    AnyService anyService;

    // Test import đa luồng và gọi trình dọn rác của JVM để giải phóng bộ nhớ
    @GetMapping("/import-multi-thread-garbage-collector")
    public ResponseEntity<?> importDataOneThread() throws InterruptedException {
        Object result = anyService.importMultiThread();
        System.gc();
        return ResponseEntity.ok(result);
    }

    // Test import đa luồng và không gọi trình dọn rác của JVM
    @GetMapping("/import-multi-thread")
    public ResponseEntity<?> importDataMultiThread() {
        return ResponseEntity.ok(anyService.importMultiThread());
    }

    // Hàm gọi garbage collector của JVM bằng tay, trình dọn rác và quản lý bộ nhớ
    @GetMapping("/call-garbage-collector")
    public ResponseEntity<?> callGarbageCollector() throws InterruptedException {
        return ResponseEntity.ok(anyService.callGarbageCollector());
    }
}
