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

    /*
    * Test với import bằng đơn luồng thì thời gian import xong nửa triệu bản ghi là 270s
    * */
    @GetMapping("/import-one-thread")
    public ResponseEntity<?> importDataOneThread() {
        return ResponseEntity.ok(anyService.importDataOneThread());
    }

    /*
     * Test với import bằng đa luồng thì thời gian import xong nửa triệu bản ghi là 49s,
     * nhanh hơn 5.5 lần so với import đơn luồng
     * */
    @GetMapping("/import-multi-thread")
    public ResponseEntity<?> importDataMultiThread() {
        return ResponseEntity.ok(anyService.importDataMultiThread());
    }
}
