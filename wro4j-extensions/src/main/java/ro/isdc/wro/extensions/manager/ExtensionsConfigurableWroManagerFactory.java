/**
 * Copyright Alex Objelean
 */
package ro.isdc.wro.extensions.manager;

import java.util.Map;

import org.apache.commons.lang3.Validate;

import ro.isdc.wro.extensions.processor.css.BourbonCssProcessor;
import ro.isdc.wro.extensions.processor.css.CssLintProcessor;
import ro.isdc.wro.extensions.processor.css.LessCssProcessor;
import ro.isdc.wro.extensions.processor.css.RubySassCssProcessor;
import ro.isdc.wro.extensions.processor.css.SassCssProcessor;
import ro.isdc.wro.extensions.processor.css.YUICssCompressorProcessor;
import ro.isdc.wro.extensions.processor.js.BeautifyJsProcessor;
import ro.isdc.wro.extensions.processor.js.CJsonProcessor;
import ro.isdc.wro.extensions.processor.js.CoffeeScriptProcessor;
import ro.isdc.wro.extensions.processor.js.DojoShrinksafeCompressorProcessor;
import ro.isdc.wro.extensions.processor.js.DustJsProcessor;
import ro.isdc.wro.extensions.processor.js.GoogleClosureCompressorProcessor;
import ro.isdc.wro.extensions.processor.js.HandlebarsJsProcessor;
import ro.isdc.wro.extensions.processor.js.HoganJsProcessor;
import ro.isdc.wro.extensions.processor.js.JsHintProcessor;
import ro.isdc.wro.extensions.processor.js.JsLintProcessor;
import ro.isdc.wro.extensions.processor.js.JsonHPackProcessor;
import ro.isdc.wro.extensions.processor.js.PackerJsProcessor;
import ro.isdc.wro.extensions.processor.js.UglifyJsProcessor;
import ro.isdc.wro.extensions.processor.js.YUIJsCompressorProcessor;
import ro.isdc.wro.manager.factory.ConfigurableWroManagerFactory;
import ro.isdc.wro.model.resource.processor.ResourceProcessor;
import ro.isdc.wro.model.resource.processor.decorator.LazyProcessorDecorator;
import ro.isdc.wro.util.LazyInitializer;

import com.google.javascript.jscomp.CompilationLevel;


/**
 * An implementation of {@link ConfigurableWroManagerFactory} that adds processors defined in extensions module.
 * 
 * @author Alex Objelean
 */
public class ExtensionsConfigurableWroManagerFactory
    extends ConfigurableWroManagerFactory {
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected void contributePostProcessors(final Map<String, ResourceProcessor> map) {
    populateMapWithExtensionsProcessors(map);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  protected void contributePreProcessors(final Map<String, ResourceProcessor> map) {
    populateMapWithExtensionsProcessors(map);
  }
  
  /**
   * Populates a map of processors with processors existing in extensions module. Use lazy initializer to avoid unused
   * dependency runtime requirement. Probably future implementation will use a different approach, by loading processors
   * from configuration property file (ex: by scanning classpath or META-INF folder). Current implementation is good
   * enough to load processors on demand.
   * 
   * @param map
   *          to populate.
   */
  public static void populateMapWithExtensionsProcessors(final Map<String, ResourceProcessor> map) {
    Validate.notNull(map);
    map.put(YUICssCompressorProcessor.ALIAS, new LazyProcessorDecorator(new LazyInitializer<ResourceProcessor>() {
      @Override
      protected ResourceProcessor initialize() {
        return new YUICssCompressorProcessor();
      }
    }));
    map.put(YUIJsCompressorProcessor.ALIAS_NO_MUNGE, new LazyProcessorDecorator(
        new LazyInitializer<ResourceProcessor>() {
          @Override
          protected ResourceProcessor initialize() {
            return YUIJsCompressorProcessor.noMungeCompressor();
          }
        }));
    map.put(YUIJsCompressorProcessor.ALIAS_MUNGE, new LazyProcessorDecorator(
        new LazyInitializer<ResourceProcessor>() {
          @Override
          protected ResourceProcessor initialize() {
            return YUIJsCompressorProcessor.doMungeCompressor();
          }
        }));
    map.put(DojoShrinksafeCompressorProcessor.ALIAS, new LazyProcessorDecorator(
        new LazyInitializer<ResourceProcessor>() {
          @Override
          protected ResourceProcessor initialize() {
            return new DojoShrinksafeCompressorProcessor();
          }
        }));
    map.put(UglifyJsProcessor.ALIAS_UGLIFY, new LazyProcessorDecorator(new LazyInitializer<ResourceProcessor>() {
      @Override
      protected ResourceProcessor initialize() {
        return new UglifyJsProcessor();
      }
    }));
    map.put(BeautifyJsProcessor.ALIAS_BEAUTIFY, new LazyProcessorDecorator(new LazyInitializer<ResourceProcessor>() {
      @Override
      protected ResourceProcessor initialize() {
        return new BeautifyJsProcessor();
      }
    }));
    map.put(PackerJsProcessor.ALIAS, new LazyProcessorDecorator(new LazyInitializer<ResourceProcessor>() {
      @Override
      protected ResourceProcessor initialize() {
        return new PackerJsProcessor();
      }
    }));
    map.put(LessCssProcessor.ALIAS, new LazyProcessorDecorator(new LazyInitializer<ResourceProcessor>() {
      @Override
      protected ResourceProcessor initialize() {
        return new LessCssProcessor();
      }
    }));
    map.put(SassCssProcessor.ALIAS, new LazyProcessorDecorator(new LazyInitializer<ResourceProcessor>() {
      @Override
      protected ResourceProcessor initialize() {
        return new SassCssProcessor();
      }
    }));
    map.put(RubySassCssProcessor.ALIAS, new LazyProcessorDecorator(new LazyInitializer<ResourceProcessor>() {
      @Override
      protected ResourceProcessor initialize() {
        return new RubySassCssProcessor();
      }
    }));
    map.put(BourbonCssProcessor.ALIAS, new LazyProcessorDecorator(new LazyInitializer<ResourceProcessor>() {
      @Override
      protected ResourceProcessor initialize() {
        return new BourbonCssProcessor();
      }
    }));
    map.put(GoogleClosureCompressorProcessor.ALIAS_SIMPLE, new LazyProcessorDecorator(
        new LazyInitializer<ResourceProcessor>() {
          @Override
          protected ResourceProcessor initialize() {
            return new GoogleClosureCompressorProcessor(CompilationLevel.SIMPLE_OPTIMIZATIONS);
          }
        }));
    map.put(GoogleClosureCompressorProcessor.ALIAS_ADVANCED, new LazyProcessorDecorator(
        new LazyInitializer<ResourceProcessor>() {
          @Override
          protected ResourceProcessor initialize() {
            return new GoogleClosureCompressorProcessor(CompilationLevel.ADVANCED_OPTIMIZATIONS);
          }
        }));
    map.put(CoffeeScriptProcessor.ALIAS, new LazyProcessorDecorator(new LazyInitializer<ResourceProcessor>() {
      @Override
      protected ResourceProcessor initialize() {
        return new CoffeeScriptProcessor();
      }
    }));
    map.put(CJsonProcessor.ALIAS_PACK, new LazyProcessorDecorator(new LazyInitializer<ResourceProcessor>() {
      @Override
      protected ResourceProcessor initialize() {
        return CJsonProcessor.packProcessor();
      }
    }));
    map.put(CJsonProcessor.ALIAS_UNPACK, new LazyProcessorDecorator(new LazyInitializer<ResourceProcessor>() {
      @Override
      protected ResourceProcessor initialize() {
        return CJsonProcessor.unpackProcessor();
      }
    }));
    map.put(JsonHPackProcessor.ALIAS_PACK, new LazyProcessorDecorator(new LazyInitializer<ResourceProcessor>() {
      @Override
      protected ResourceProcessor initialize() {
        return JsonHPackProcessor.packProcessor();
      }
    }));
    map.put(JsonHPackProcessor.ALIAS_UNPACK, new LazyProcessorDecorator(new LazyInitializer<ResourceProcessor>() {
      @Override
      protected ResourceProcessor initialize() {
        return JsonHPackProcessor.unpackProcessor();
      }
    }));
    map.put(JsHintProcessor.ALIAS, new LazyProcessorDecorator(new LazyInitializer<ResourceProcessor>() {
      @Override
      protected ResourceProcessor initialize() {
        return new JsHintProcessor();
      }
    }));
    map.put(JsLintProcessor.ALIAS, new LazyProcessorDecorator(new LazyInitializer<ResourceProcessor>() {
      @Override
      protected ResourceProcessor initialize() {
        return new JsLintProcessor();
      }
    }));
    map.put(CssLintProcessor.ALIAS, new LazyProcessorDecorator(new LazyInitializer<ResourceProcessor>() {
      @Override
      protected ResourceProcessor initialize() {
        return new CssLintProcessor();
      }
    }));
    map.put(DustJsProcessor.ALIAS, new LazyProcessorDecorator(new LazyInitializer<ResourceProcessor>() {
      @Override
      protected ResourceProcessor initialize() {
        return new DustJsProcessor();
      }
    }));
    map.put(HoganJsProcessor.ALIAS, new LazyProcessorDecorator(new LazyInitializer<ResourceProcessor>() {
      @Override
      protected ResourceProcessor initialize() {
        return new HoganJsProcessor();
      }
    }));
    map.put(HandlebarsJsProcessor.ALIAS, new LazyProcessorDecorator(new LazyInitializer<ResourceProcessor>() {
      @Override
      protected ResourceProcessor initialize() {
        return new HandlebarsJsProcessor();
      }
    }));
  }
}
