module.exports = (grunt) ->
  grunt.initConfig
    pkg: grunt.file.readJSON('package.json')
    dirs:
      root: 'src/main/resources/static'
      coffee: '<%= dirs.root %>/coffee'
      dev: '<%= dirs.root %>/jssrc'
      dest: '<%= dirs.root %>/js'
      scss: '<%= dirs.root %>/scss'
      css: '<%= dirs.root %>/css'

    coffee:
      app:
        files:
          '<%= dirs.dev %>/kitbus.js': [
            '<%= dirs.coffee %>/App.coffee'
            '<%= dirs.coffee %>/model/*.coffee'
            '<%= dirs.coffee %>/view/*.coffee'
            '<%= dirs.coffee %>/Init.coffee'
          ]

    uglify:
      app:
        options:
          preserveComments: 'some' # /*!で始まるコメントを消さない
          sourceMap: true
          sourceMapName: '<%= dirs.dev %>/kitbus.map'
        files:
          '<%= dirs.dest %>/kitbus.min.js': ['<%= dirs.dev %>/kitbus.js']

    sass:
      app:
        options:
          style: 'compressed'
        files: '<%= dirs.css %>/main.css': '<%= dirs.scss %>/main.scss'

    watch:
      scripts:
        files: ['**/*.coffee']
        tasks: ['default']
        options:
          interrupt: true

    clean:
      all: [
        '<%= dirs.dest %>'
        '<%= dirs.dev %>'
        'node_modules'
      ]
      dev: [
        '<%= dirs.dest %>'
        '<%= dirs.dev %>'
      ]

  grunt.loadNpmTasks('grunt-contrib-clean')
  grunt.loadNpmTasks('grunt-contrib-coffee')
  grunt.loadNpmTasks('grunt-contrib-sass')
  grunt.loadNpmTasks('grunt-contrib-uglify')
  grunt.loadNpmTasks('grunt-contrib-watch')

  grunt.registerTask('default', ['coffee', 'uglify'])
