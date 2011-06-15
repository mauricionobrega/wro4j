/**
 * Copyright Alex Objelean
 */
package ro.isdc.wro.extensions.manager;

import java.util.HashMap;
import java.util.Map;

import ro.isdc.wro.extensions.processor.css.LessCssProcessor;
import ro.isdc.wro.extensions.processor.css.SassCssProcessor;
import ro.isdc.wro.extensions.processor.css.YUICssCompressorProcessor;
import ro.isdc.wro.extensions.processor.js.BeautifyJsProcessor;
import ro.isdc.wro.extensions.processor.js.CoffeeScriptProcessor;
import ro.isdc.wro.extensions.processor.js.DojoShrinksafeCompressorProcessor;
import ro.isdc.wro.extensions.processor.js.GoogleClosureCompressorProcessor;
import ro.isdc.wro.extensions.processor.js.PackerJsProcessor;
import ro.isdc.wro.extensions.processor.js.UglifyJsProcessor;
import ro.isdc.wro.extensions.processor.js.YUIJsCompressorProcessor;
import ro.isdc.wro.manager.factory.ConfigurableWroManagerFactory;
import ro.isdc.wro.model.resource.processor.ResourceProcessor;
import ro.isdc.wro.model.resource.processor.impl.css.CssDataUriPreProcessor;

import com.google.javascript.jscomp.CompilationLevel;


/**
 * An implementation of {@link ConfigurableWroManagerFactory} that adds processors defined in extensions module.
 *
 * @author Alex Objelean
 */
public class ExtensionsConfigurableWroManagerFactory extends ConfigurableWroManagerFactory {
  /**
   * {@inheritDoc}
   */
  @Override
  protected void contributePostProcessors(final Map<String, ResourceProcessor> map) {
    map.putAll(createCommonProcessors());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void contributePreProcessors(final Map<String, ResourceProcessor> map) {
    map.putAll(createCommonProcessors());
    map.put("cssDataUri", new CssDataUriPreProcessor());
  }

  /**
   * @return a map of processors to be used as both: pre & post processor.
   */
  private Map<String, ResourceProcessor> createCommonProcessors() {
    final Map<String, ResourceProcessor> map = new HashMap<String, ResourceProcessor>();
    map.put("yuiCssMin", new YUICssCompressorProcessor());
    map.put("yuiJsMin", YUIJsCompressorProcessor.noMungeCompressor());
    map.put("yuiJsMinAdvanced", YUIJsCompressorProcessor.doMungeCompressor());
    map.put("dojoShrinksafe", new DojoShrinksafeCompressorProcessor());
    map.put("uglifyJs", new UglifyJsProcessor());
    map.put("beautifyJs", new BeautifyJsProcessor());
    map.put("packerJs", new PackerJsProcessor());
    map.put("lessCss", new LessCssProcessor());
    map.put("sassCss", new SassCssProcessor());
    map.put("googleClosureSimple", new GoogleClosureCompressorProcessor());
    map.put("googleClosureAdvanced", new GoogleClosureCompressorProcessor(CompilationLevel.ADVANCED_OPTIMIZATIONS));
    map.put("coffeeScript", new CoffeeScriptProcessor());
    map.put("cssDataUri", new CssDataUriPreProcessor());
    return map;
  }
}
