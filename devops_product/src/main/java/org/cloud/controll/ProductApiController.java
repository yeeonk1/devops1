package org.cloud.controll;

import java.util.List;

import org.cloud.dto.ProductDTO;
import org.cloud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// github test pull
@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductApiController {

	@Autowired	
	private ProductService productService;
	
	@GetMapping("/list")
	public List<ProductDTO> openProductList(Model model) throws Exception {
		List<ProductDTO> list = productService.productList();
		model.addAttribute("list", list);
		
		return list; // 그냥 list만 반환
	}
	
	@GetMapping("/write")
	public String openProductWrite() throws Exception {
		return "productWriteUI";
	}
	
	@PostMapping("/insert")
	public ResponseEntity<String> insertProduct(@RequestBody ProductDTO product) throws Exception {
		try {
			productService.insertProduct(product);
			return ResponseEntity.ok("success");
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("fail: " + e.getMessage());
		}
	}
	
	/* 
	  	@PostMapping("/insert")
	  	public String insertProduct(@RequestBody ProductDTO product) throws Exception {
	  		productService.insertProduct(product);
			return "insert success";
		} 이런 식으로 해도 상관 없음! (update, delete도 해당)
	 */
	
	@GetMapping("/detail/{num}")
	public String openProductDetail(@PathVariable ("num") int num, Model model) throws Exception{
		
		ProductDTO product = productService.productDetail(num);
		model.addAttribute("product", product);
		
		return "productDetail";
	}
	
	@PutMapping("/update/{num}")
	public ResponseEntity<String> updateProduct(@PathVariable("num") int num, @RequestBody ProductDTO product) throws Exception {
		product.setNum(num); // 숫자 먼저 확인!
		productService.updateProduct(product);
		return ResponseEntity.ok("success");
	}
	
	@DeleteMapping("/delete/{num}")
	public ResponseEntity<String> deleteProduct(@PathVariable("num") int num) throws Exception {
		
		productService.deleteProduct(num);
		return ResponseEntity.ok("success");
	}
}
