package info.smartfactory.domain.node.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.smartfactory.domain.node.entity.Node;
import info.smartfactory.domain.node.service.NodeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/map/simul")
public class NodeSimulController {

	private final NodeService nodeService;

	@GetMapping
	public void getMap() {
		List<Node> map = nodeService.getMap();
	}

}
